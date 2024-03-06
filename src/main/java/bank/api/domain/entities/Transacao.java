package bank.api.domain.entities;

import bank.api.domain.enums.TipoTransacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "Transacao")
@Table(name = "transacoes")
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", length = 30)
    private TipoTransacao tipoTransacao;

    @Column(name = "valor", nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_conta", nullable = false)
    private Conta conta;

    @ManyToOne()
    @JoinColumn(name = "id_conta_destino")
    private Conta contaDestino;

}