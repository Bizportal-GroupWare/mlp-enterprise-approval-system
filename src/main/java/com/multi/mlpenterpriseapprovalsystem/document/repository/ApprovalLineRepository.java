package com.multi.mlpenterpriseapprovalsystem.document.repository;

import com.multi.mlpenterpriseapprovalsystem.document.domain.ApprovalLine;
import com.multi.mlpenterpriseapprovalsystem.document.domain.Document;
import com.multi.mlpenterpriseapprovalsystem.document.enums.ApprStat;
import com.multi.mlpenterpriseapprovalsystem.document.enums.DocStat;
import com.multi.mlpenterpriseapprovalsystem.employee.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 결재라인 테이블 관리 레포지토리
 *
 * @author : 이지헌
 * @filename : ApprovalLineRepository
 * @since : 25. 12. 18. 목요일.
 */
public interface ApprovalLineRepository extends JpaRepository<ApprovalLine, Long> {

    List<ApprovalLine> findByDocument_docNo(Long docNo);

    @Modifying
    void deleteByDocument_docNo(Long docNo);

    // 휴가 종료 시 삭제할 대직자 결재라인 ID 조회(주석 내용 체크 필요)
    @Query("SELECT al.apprlNo FROM ApprovalLine al " +
            "WHERE al.isDelegate = true " +
            "AND al.approver = :delegate " +
            "AND al.targetApprover = :onLeave " + // 대직 대상자 직접 확인
            "AND al.apprStat IN (:apprStats) " +
            "AND al.document.docStat IN (:docStats)")
    List<Long> findDelegateApprovalLineIdsToDelete(
            @Param("onLeave") Employee onLeave,
            @Param("delegate") Employee delegate,
            @Param("docStats") DocStat[] docStats,
            @Param("apprStats") ApprStat[] apprStats
    );

    // 결재라인 ID 목록으로 삭제
    @Modifying
    @Query("DELETE FROM ApprovalLine al WHERE al.apprlNo IN :apprlNos")
    void deleteByApprlNoIn(@Param("apprlNos") List<Long> apprlNos);

    /**
     * 문서상태가 US(임시저장), 결재중(AW)이고,
     * 휴가자의 결재상태가 I(결재중), W(결재대기중)인 문서에서
     * 휴가자의 결재라인 조회
     */
    @Query("SELECT al FROM ApprovalLine al " +
            /* "WHERE al.isDelegate = false " + */ //휴가자가 원결재자가 아닌 대직자일 수도 있음
            "WHERE al.approver = :onLeave " +
            "AND al.apprStat IN (:apprStats) " +
            "AND al.document.docStat IN (:docStats)")
    List<ApprovalLine> findApprovalLinesForVacation(
            @Param("onLeave") Employee onLeave,
            @Param("docStats") DocStat[] docStats,
            @Param("apprStats") ApprStat[] apprStats
    );

    boolean existsByDocumentAndSeqAndApproverAndIsDelegateAndTargetApprover(
            Document document,
            int seq,
            Employee approver,
            Boolean isDelegate,
            Employee targetApprover
    );

    // 내가 결재자이면서 결재 상태가 AWAITING인 문서 개수 카운트
    int countByApproverAndApprStat(Employee approver, ApprStat apprStat);
}
