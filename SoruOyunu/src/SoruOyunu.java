import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoruOyunu{
    private List<Soru> soruHavuzu;
    private List<Soru> secilenSorular;
    private int mevcutSoruIndeksi;

    public SoruOyunu() {
        soruHavuzu = new ArrayList<>();
        secilenSorular = new ArrayList<>();
        veritabanindanSorulariYukle();
        rastgeleSorulariSec();
        mevcutSoruIndeksi = 0;
    }

    private void veritabanindanSorulariYukle() {
        String url = "jdbc:postgresql://localhost:5432/SoruOyunu";  // PostgreSQL veritabanı URL'si
        String kullanici = "postgres";  // Veritabanı kullanıcı adı
        String sifre = "emir0533";  // Veritabanı şifresi
        String sql = "SELECT soru_metni, secenek1, secenek2, secenek3, secenek4, dogru_secenek FROM sorular";

        try (Connection conn = DriverManager.getConnection(url, kullanici, sifre);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String soruMetni = rs.getString("soru_metni");
                String[] secenekler = new String[4];
                secenekler[0] = rs.getString("secenek1");
                secenekler[1] = rs.getString("secenek2");
                secenekler[2] = rs.getString("secenek3");
                secenekler[3] = rs.getString("secenek4");
                int dogruSecenek = rs.getInt("dogru_secenek") - 1;  // Veritabanında 1 tabanlı indeks, burada 0 tabanlı
                soruHavuzu.add(new Soru(soruMetni, secenekler, dogruSecenek));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void rastgeleSorulariSec() {
        Collections.shuffle(soruHavuzu);  // Soru havuzunu karıştır
        for (int i = 0; i < 20; i++) {    // İlk 20 soruyu seç
            secilenSorular.add(soruHavuzu.get(i));
        }
    }

    public Soru getMevcutSoru() {
        return secilenSorular.get(mevcutSoruIndeksi);
    }

    public void sonrakiSoru() {
        if (mevcutSoruIndeksi < secilenSorular.size() - 1) {
            mevcutSoruIndeksi++;
        }
    }

    public void oncekiSoru() {
        if (mevcutSoruIndeksi > 0) {
            mevcutSoruIndeksi--;
        }
    }

    public int getMevcutSoruIndeksi() {
        return mevcutSoruIndeksi;
    }

    public int getToplamSoru() {
        return secilenSorular.size();
    }
}
