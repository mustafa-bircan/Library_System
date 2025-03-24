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
            throw new IllegalArgumentException("Üye kimliği boş veya null olamaz");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ad boş veya null olamaz");
        }
        this.memberId = memberId;
        this.name = name;
    }

    public MemberBuilder address(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Adres boş veya null olamaz");
        }
        if (address.length() < 5) {
            throw new IllegalArgumentException("Adres en az 5 karakter olmalıdır");
        }
        this.address = address;
        return this;
    }

    public MemberBuilder phoneNo(String phoneNo) {
        if (phoneNo == null || phoneNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Telefon numarası boş veya null olamaz");
        }
        if (!phoneNo.matches("\\d{3}-\\d{4}")) {
            throw new IllegalArgumentException("Telefon numarası formatta olmalıdır: XXX-XXXX");
        }
        this.phoneNo = phoneNo;
        return this;
    }

    public MemberBuilder type(ReaderLimit type) {
        if (type == null) {
            throw new IllegalArgumentException("Üye tipi null olamaz");
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
        if (address == null) missingFields.add("adres");
        if (phoneNo == null) missingFields.add("telefon numarası");
        if (type == null) missingFields.add("üye tipi");

        if (!missingFields.isEmpty()) {
            throw new IllegalStateException(
                    "MemberRecord oluşturulamıyor. Eksik gerekli alanlar: " +
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
