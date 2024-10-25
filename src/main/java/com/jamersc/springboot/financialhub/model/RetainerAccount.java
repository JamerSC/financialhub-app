package com.jamersc.springboot.financialhub.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client_retainer_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"clientAccount"})
public class RetainerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "retainer_id")
    private Long retainerId;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_account_id")
    private ClientAccount clientAccount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
}
