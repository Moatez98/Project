package tn.moatez.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class AbstractBaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;


    @CreatedBy
    @Column(name = "CREATOR", updatable = false)
    private String creator;


    @CreatedDate
    @Column(name = "CREATED", updatable = false)
    protected LocalDateTime created;


    @LastModifiedBy
    @Column(name = "MODIFIER")
    private String modifier;


    @LastModifiedDate
    @Column(name = "MODIFIED")
    private LocalDateTime modified;

    @Version
    @Column(name = "VERSION")
    private Integer version;
}
