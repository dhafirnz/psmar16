package nz.co.threepoints.ps.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import nz.co.threepoints.ps.domain.enumeration.AddressType;

/**
 * A CompanyAddress.
 */
@Entity
@Table(name = "company_address")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "companyaddress")
public class CompanyAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Size(max = 100)
    @Column(name = "address_line1", length = 100)
    private String addressLine1;
    
    @Size(max = 100)
    @Column(name = "address_line2", length = 100)
    private String addressLine2;
    
    @Size(max = 30)
    @Column(name = "suburb", length = 30)
    private String suburb;
    
    @Size(max = 30)
    @Column(name = "town", length = 30)
    private String town;
    
    @Size(max = 30)
    @Column(name = "city", length = 30)
    private String city;
    
    @Size(max = 8)
    @Column(name = "postcode", length = 8)
    private String postcode;
    
    @Size(max = 30)
    @Column(name = "state", length = 30)
    private String state;
    
    @Size(max = 30)
    @Column(name = "country", length = 30)
    private String country;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AddressType type;
    
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressLine1() {
        return addressLine1;
    }
    
    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }
    
    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getSuburb() {
        return suburb;
    }
    
    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getTown() {
        return town;
    }
    
    public void setTown(String town) {
        this.town = town;
    }

    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }
    
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }

    public AddressType getType() {
        return type;
    }
    
    public void setType(AddressType type) {
        this.type = type;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyAddress companyAddress = (CompanyAddress) o;
        if(companyAddress.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, companyAddress.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyAddress{" +
            "id=" + id +
            ", addressLine1='" + addressLine1 + "'" +
            ", addressLine2='" + addressLine2 + "'" +
            ", suburb='" + suburb + "'" +
            ", town='" + town + "'" +
            ", city='" + city + "'" +
            ", postcode='" + postcode + "'" +
            ", state='" + state + "'" +
            ", country='" + country + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
