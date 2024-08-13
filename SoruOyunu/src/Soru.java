public class Soru {
    private String soruMetni;
    private String[] secenekler;
    private int dogruSecenek;

    public Soru(String soruMetni, String[] secenekler, int dogruSecenek) {
        this.soruMetni = soruMetni;
        this.secenekler = secenekler;
        this.dogruSecenek = dogruSecenek;
    }

    public String getSoruMetni() {
        return soruMetni;
    }

    public String[] getSecenekler() {
        return secenekler;
    }

    public int getDogruSecenek() {
        return dogruSecenek;
    }
}
