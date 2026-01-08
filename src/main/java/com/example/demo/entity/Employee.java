package com.example.demo.entity;
import com.example.demo.enums.EmployeeStatus;
import jakarta.persistence.*; import jdk.jfr.Timestamp;
import lombok.AllArgsConstructor; import lombok.Getter;
import lombok.NoArgsConstructor; import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.annotation.processing.Generated; import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity @Table(name="employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(nullable = false,unique = true)
    private String email;
    private String phoneNumber;
    @Column(unique = true)
    private String employeeCode;
    private LocalDate dateOfJoining;
    private String department;
    private String designation;
    @ManyToOne @JoinColumn(name="manager_id")
    private Employee manager;
    @Enumerated(EnumType.STRING)
    private EmployeeStatus status;
    @CreationTimestamp @Column(updatable=false)
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}