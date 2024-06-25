package com.project.springbootblogapplication.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Authority extends BaseModel implements Serializable {

    @Enumerated(EnumType.ORDINAL)
    private AuthorityType authorityName;

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    @Override
    public String toString(){
        return "Authority{" + "name='" + authorityName + "'" + "}";
    }

}
