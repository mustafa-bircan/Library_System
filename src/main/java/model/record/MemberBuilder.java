package main.java.model.record;

import main.java.model.person.enums.ReaderLimit;

import java.util.ArrayList;
import java.util.List;

public class MemberBuilder {
    private final String memberId;
    private final String name;
    private String address;
    private String phoneNo;
    private ReaderLimit type;

    public MemberBuilder(String memberId,String name) {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("Member ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.memberId = memberId;
        this.name = name;
    }

    public MemberBuilder address(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }
        if (address.length() < 5) {
            throw new IllegalArgumentException("Address must be at least 5 characters");
        }
        this.address = address;
        return this;
    }

    public MemberBuilder phoneNo(String phoneNo) {
        if (phoneNo == null || phoneNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be null or empty");
        }
        if (!phoneNo.matches("\\d{3}-\\d{4}")) {
            throw new IllegalArgumentException("Phone number must be in format: XXX-XXXX");
        }
        this.phoneNo = phoneNo;
        return this;
    }

    public MemberBuilder type(ReaderLimit type) {
        if (type == null) {
            throw new IllegalArgumentException("Member type cannot be null");
        }
        this.type = type;
        return this;
    }

    public MemberRecord build() {
        validateAllFields();
        return new MemberRecord(this);
    }

    private void validateAllFields() {
        List<String> missingFields = new ArrayList<>();
        if (address == null) missingFields.add("address");
        if (phoneNo == null) missingFields.add("phone number");
        if (type == null) missingFields.add("member type");

        if (!missingFields.isEmpty()) {
            throw new IllegalStateException(
                    "Cannot build MemberRecord. Missing required fields: " +
                            String.join(", ", missingFields)
            );
        }
    }

    String getMemberId() { return memberId; }
    String getName() { return name; }
    String getAddress() { return address; }
    String getPhoneNo() { return phoneNo; }
    ReaderLimit getType() { return type; }
}
