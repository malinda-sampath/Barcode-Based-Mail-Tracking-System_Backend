package com.UoR_MTS_Backend.mail_tracking_system.repositories;

import com.UoR_MTS_Backend.mail_tracking_system.dtos.response.MailRecordResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MailRecordRepositoryImpl implements MailRecordRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<MailRecordResponseDTO> getMailRecordsFromDynamicTable(String tableName) {
        // Note: Use explicit column names instead of SELECT * for clarity and safety
        String sql = "SELECT sender_name, receiver_name, mail_type, tracking_number, barcode_id, mail_description, " +
                "barcode_image, branch_code, location, status, reference_number, insert_date_time, update_date_time " +
                "FROM " + tableName;

        Query query = entityManager.createNativeQuery(sql);

        List<Object[]> results = query.getResultList();
        List<MailRecordResponseDTO> dtoList = new ArrayList<>();

        // Process each row from the result
        for (Object[] row : results) {
            MailRecordResponseDTO dto = new MailRecordResponseDTO();
            dto.setSenderName(castToString(row[0]));
            dto.setReceiverName(castToString(row[1]));
            dto.setMailType(castToString(row[2]));
            dto.setTrackingNumber(castToString(row[3]));
            dto.setBarcodeId(castToString(row[4]));
            dto.setMailDescription(castToString(row[5]));
            dto.setBarcodeImage(row[6] != null ? (byte[]) row[6] : null);
            dto.setBranchName(castToString(row[7]));
            dto.setLocation(castToString(row[8]));
            dto.setStatus(castToString(row[9]));
            dto.setReferenceNumber(castToString(row[10]));
            dto.setInsertDateTime(castToLocalDateTime(row[11]));
            dto.setUpdateDateTime(castToLocalDateTime(row[12]));

            dtoList.add(dto);
        }

        return dtoList;
    }

    // Helper methods should be outside the getMailRecordsFromDynamicTable method
    private String castToString(Object obj) {
        return obj != null ? obj.toString() : null;
    }

    private LocalDateTime castToLocalDateTime(Object obj) {
        if (obj instanceof Timestamp) {
            return ((Timestamp) obj).toLocalDateTime();
        } else if (obj instanceof LocalDateTime) {
            return (LocalDateTime) obj;
        }
        return null;
    }
}
