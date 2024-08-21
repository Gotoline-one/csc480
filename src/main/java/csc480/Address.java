package csc480.Branched;

class Address {
    private int streetNumber;
    private String streetName;
    private String city;
    private String state;
    private int zipCode;
    public Address(int streetNumber, String streetName, String city, String state, int zipCode) {
        this.streetNumber = streetNumber;
        this.streetName = streetName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
    public String addressToString() {
        return streetNumber + " " + streetName + ", " + city + ", " + state + " " + zipCode;
    }
}