package botboy;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import lombok.SneakyThrows;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Methods {

    Map<Long, String> tanla = new HashMap<>();

    public static final String QR_CODE = "qr_code.jpg";

    @SneakyThrows
    public void qrcode(String text) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, 300, 300);
        Path path = FileSystems.getDefault().getPath(QR_CODE);
        MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);
    }
}
