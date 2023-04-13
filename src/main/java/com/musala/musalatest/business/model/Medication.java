package com.musala.musalatest.business.model;


import com.musala.musalatest.util.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Medication extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "invalid_name")
    private String name;

    private int weight;

    @Column(unique = true, nullable = false)
    @Pattern(regexp = "^[A-Z0-9_-]+$", message = "Code must contain uppercase letters and numbers only!")
    private String code;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private FileStorage fileStorage;

}
