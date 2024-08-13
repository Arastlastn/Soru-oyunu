import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SoruUygulamasi {
    private JFrame cerceve;
    private JPanel panel;
    private JLabel soruEtiketi;
    private JRadioButton[] secenekButonlari;
    private ButtonGroup secenekGrubu;
    private JButton sonrakiButon;
    private JButton oncekiButon;
    private JButton kontrolButon;
    private JLabel sonucEtiketi;
    private SoruOyunu soruOyunu;

    public SoruUygulamasi() {
        soruOyunu = new SoruOyunu();

        cerceve = new JFrame("Soru Oyunu");
        panel = new JPanel();
        soruEtiketi = new JLabel();
        secenekButonlari = new JRadioButton[4];
        secenekGrubu = new ButtonGroup();
        sonrakiButon = new JButton("Sonraki");
        oncekiButon = new JButton("Önceki");
        kontrolButon = new JButton("Cevabı Kontrol Et");
        sonucEtiketi = new JLabel("");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(soruEtiketi);

        for (int i = 0; i < 4; i++) {
            secenekButonlari[i] = new JRadioButton();
            secenekGrubu.add(secenekButonlari[i]);
            panel.add(secenekButonlari[i]);
        }

        panel.add(sonrakiButon);
        panel.add(oncekiButon);
        panel.add(kontrolButon);
        panel.add(sonucEtiketi);

        sonrakiButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soruOyunu.sonrakiSoru();
                soruyuGuncelle();
                sonucEtiketi.setText("");
                secenekGrubu.clearSelection();
            }
        });

        oncekiButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soruOyunu.oncekiSoru();
                soruyuGuncelle();
                sonucEtiketi.setText("");
                secenekGrubu.clearSelection();
            }
        });

        kontrolButon.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cevabiKontrolEt();
            }
        });

        cerceve.add(panel);
        cerceve.setSize(400, 300);
        cerceve.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cerceve.setVisible(true);

        soruyuGuncelle();
    }

    private void soruyuGuncelle() {
        Soru mevcutSoru = soruOyunu.getMevcutSoru();
        soruEtiketi.setText(mevcutSoru.getSoruMetni());
        String[] secenekler = mevcutSoru.getSecenekler();
        for (int i = 0; i < 4; i++) {
            secenekButonlari[i].setText(secenekler[i]);
        }
    }

    private void cevabiKontrolEt() {
        Soru mevcutSoru = soruOyunu.getMevcutSoru();
        int dogruSecenek = mevcutSoru.getDogruSecenek();
        boolean dogruMu = false;
        for (int i = 0; i < 4; i++) {
            if (secenekButonlari[i].isSelected() && i == dogruSecenek) {
                dogruMu = true;
                break;
            }
        }
        if (dogruMu) {
            sonucEtiketi.setText("Doğru!");
        } else {
            sonucEtiketi.setText("Yanlış. Doğru cevap: " + mevcutSoru.getSecenekler()[dogruSecenek]);
        }
    }

    public static void main(String[] args) {
        new SoruUygulamasi();
    }
}
