package com.UoR_MTS_Backend.mail_tracking_system.utill.barcode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class BarcodeIDGenerator {
    private BarcodeIDGenerator() {
    }

    public static String generateUniqueId() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String uuidPart = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8); // Take 8 characters of the UUID
        return "MAIL - " + timestamp + uuidPart;
    }
}
