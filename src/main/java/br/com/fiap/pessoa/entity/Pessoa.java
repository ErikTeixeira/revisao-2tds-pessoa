package br.com.fiap.pessoa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "TB_PESSOA", uniqueConstraints = {
        @UniqueConstraint(name = "UK_EMAIL", columnNames = "EMAIL_PESSOA"),
        @UniqueConstraint(name = "UK_CPF", columnNames = "CPF_PESSOA")
})
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PESSOA")
    @SequenceGenerator(name = "SQ_PESSOA", sequenceName = "SQ_PESSOA", allocationSize = 1)
    @Column(name = "ID_PESSOA")
    private Long id;

    @Column(name = "NM_PESSOA")
    private String nome;

    @Column(name = "SN_PESSOA")
    private String sobrenome;

    @Column(name = "EMAIL_PESSOA")
    private String email;

    @Column(name = "CPF_PESSOA")
    private String cpf;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "TB_FOTO_PESSOA",
            joinColumns = {
                    @JoinColumn(
                            name = "PESSOA",
                            referencedColumnName = "ID_PESSOA",
                            foreignKey = @ForeignKey(name = "FK_PESSOA_FOTO")
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "FOTO",
                            referencedColumnName = "ID_FOTO",
                            foreignKey = @ForeignKey(name = "FK_FOTO_PESSOA")
                    )
            }
    )
    private Set<Foto> fotos = new LinkedHashSet<>();

}
