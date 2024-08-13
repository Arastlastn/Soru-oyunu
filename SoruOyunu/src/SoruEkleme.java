import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SoruEkleme {
    private static final String URL = "jdbc:postgresql://localhost:5432/SoruOyunu";  // PostgreSQL veritabanı URL'si
    private static final String KULLANICI = "postgres";  // Veritabanı kullanıcı adı
    private static final String SIFRE = "emir0533";  // Veritabanı şifresi

    public static void main(String[] args) {
        SoruEkleme soruEkleme = new SoruEkleme();
        soruEkleme.sorulariEkle();
    }

    public void sorulariEkle() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Soru Metni (Çıkmak için 'q' tuşlayın): ");
            String soruMetni = scanner.nextLine();
            if (soruMetni.equalsIgnoreCase("q")) {
                break;
            }

            String[] secenekler = new String[4];
            for (int i = 0; i < 4; i++) {
                System.out.print((i + 1) + ". Seçenek: ");
                secenekler[i] = scanner.nextLine();
            }

            System.out.print("Doğru Seçenek (1-4): ");
            int dogruSecenek = scanner.nextInt();
            scanner.nextLine();  // Buffer temizleme

            soruEkle(soruMetni, secenekler, dogruSecenek);
        }
    }

    public void soruEkle(String soruMetni, String[] secenekler, int dogruSecenek) {
        String sql = "INSERT INTO sorular (soru_metni, secenek1,  secenek2,  secenek3,  secenek4, dogru_secenek) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, KULLANICI, SIFRE);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, soruMetni);
            pstmt.setString(2, secenekler[0]);
            pstmt.setString(3, secenekler[1]);
            pstmt.setString(4, secenekler[2]);
            pstmt.setString(5, secenekler[3]);
            pstmt.setInt(6, dogruSecenek);

            pstmt.executeUpdate();
            System.out.println("Soru başarıyla eklendi!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
