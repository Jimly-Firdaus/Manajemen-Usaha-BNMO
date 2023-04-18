public class VIP extends Member {
    /* Attributes */
    protected float discountRate;

    /* Constructor */
    public VIP(int id, String name, String phoneNumber) {
        super(id, name, phoneNumber);
        this.discountRate = 10;
    }

    /* Getter */
    public float getDiscountRate() {
        return this.discountRate;
    }
    
    /* Setter */
    public void setDiscountRate(float discountRate) {
        this.discountRate = discountRate;
    }

    /* Other Methods */
}
