package sb.lon.model;

/**
 * Created by sblon on 12/05/2017.
 */
public class Card {
    private Long id;
    private Integer pin;
    private boolean isActive;
    private Integer pinEnteredCount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPin() {
        return pin;
    }

    public void setPin(Integer pin) {
        this.pin = pin;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getPinEnteredCount() {
        return pinEnteredCount;
    }

    public void setPinEnteredCount(Integer pinEnteredCount) {
        this.pinEnteredCount = pinEnteredCount;
    }

    @Override
    public String toString() {
        return "Card{" + "id=" + id + ", pin=" + pin + ", isActive=" + isActive + ", pinEnteredCount=" + pinEnteredCount + '}';
    }
}
