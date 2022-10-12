package app.entities;

import lombok.*;
import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String gender;

    private Date birthdate;

    @Column(unique = true)
    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Role> roles;
}
