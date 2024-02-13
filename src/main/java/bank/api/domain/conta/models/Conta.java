package bank.api.domain.conta.models;

import bank.api.domain.cliente.models.Cliente;
import bank.api.domain.conta.enums.TipoConta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "Conta")
@Table(name = "contas")
@AllArgsConstructor
@NoArgsConstructor
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "saldo", precision = 10)
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_conta", nullable = false, length = 30)
    private TipoConta tipoConta;

    @Column(name = "data_de_criacao", nullable = false)
    private LocalDateTime data;

    @Column(name = "esta_ativa", nullable = false)
    private Boolean estaAtiva;

    public void delete() {
        this.estaAtiva = false;
    }
}