package com.dukankhata.returns;
import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ReturnRefundRepository extends JpaRepository<ReturnRefund,UUID>{ List<ReturnRefund> findBySaleReturnId(UUID returnId); }
