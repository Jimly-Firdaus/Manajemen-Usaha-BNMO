public class Member extends Customer {
    /* Attributes */
    protected String name;
    protected String phoneNumber;
    protected float point;

    /* Constructor */
    public Member(int id, String name, String phoneNumber) {
        super(id);
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.point = 0;
        /* To Do */
        /* paymentHistory: Payment[] */
    }

    /* Getter */
    public String getName() {
        return this.name;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public float getPoint() {
        return this.point;
    }

    /* Setter */
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /* Other Methods */
    public void atrievePoint(int purchaseAmount) {
        this.point += purchaseAmount*(0.01); /* 1% of total purchase */
    }

    public float convertPointToDiscount() {
        return this.point; /* 1 point = 1 Rupiah */
    }

    /* Upgrade to VIP */
    public VIP upgradeToVIP(String name, String phoneNumber) {
        return new VIP(this.getId(), name, phoneNumber);
    }
}