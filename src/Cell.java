/**
 * Created by john_bachman on 12/14/15.
 */
public class Cell {
    private int status = 0;
    private boolean diseased = false;
    private boolean cured = false;

    public Cell(int status) {
        this.status = status;
    }

    public void setDiseased(boolean diseased) {
        this.diseased = diseased;
    }

    public boolean isDiseased() {
        return diseased;
    }

    public void age() {
        status++;
    }

    public void setCured(boolean cured) {
        this.cured = cured;
    }

    public boolean isCured() {
        return cured;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
