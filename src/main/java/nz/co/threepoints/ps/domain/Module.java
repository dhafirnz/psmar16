package nz.co.threepoints.ps.domain;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * Module
 */
@ApiModel(description = ""
    + "Module")
@Entity
@Table(name = "module")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "module")
public class Module implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 5, max = 20)
    @Column(name = "code", length = 20, nullable = false)
    private String code;
    
    @NotNull
    @Size(min = 5, max = 100)
    @Column(name = "name", length = 100, nullable = false)
    private String name;
    
    @Size(max = 100)
    @Column(name = "resource", length = 100)
    private String resource;
    
    @ManyToMany(mappedBy = "modules")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Company> companys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getResource() {
        return resource;
    }
    
    public void setResource(String resource) {
        this.resource = resource;
    }

    public Set<Company> getCompanys() {
        return companys;
    }

    public void setCompanys(Set<Company> companys) {
        this.companys = companys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Module module = (Module) o;
        if(module.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, module.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Module{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", resource='" + resource + "'" +
            '}';
    }
}
