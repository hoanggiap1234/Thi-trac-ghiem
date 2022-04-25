package com.itsol.recruit.entity;

import com.itsol.recruit.core.Constants;
import lombok.Data;
import lombok.Value;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import java.util.UUID;
import javax.persistence.*;
import java.util.Random;



@Entity
@Data
public class OTP {

    private final static Long EXPIRED_TIME = Constants.OTP.EXPIRED_TIME;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID id;

    private String code;

    private Long issueAt;
    @OneToOne
    private User user;

    public OTP(User user){
        this.user = user;
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        do{
            sb.append(String.valueOf(random.nextInt(10)));
        }while (sb.length() < 5);
        code = sb.toString();
        issueAt = System.currentTimeMillis();
    }

    public OTP(){
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        do{
            sb.append(String.valueOf(random.nextInt(10)));
        }while (sb.length() < 5);
        this.code = sb.toString();
        this.issueAt = System.currentTimeMillis();
    }

    public boolean isExpired(){
        return this.issueAt + EXPIRED_TIME < System.currentTimeMillis();
    }
}
