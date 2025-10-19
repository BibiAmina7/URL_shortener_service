package com.company.urlshortener.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clicks")
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "url_id")
    private Url url;

    @Column(name = "clicked_at")
    private LocalDateTime clickedAt = LocalDateTime.now();

    private String referrer;
    private String ip;

    public Click() {}
    public Click(Url url, String referrer, String ip) {
        this.url = url;
        this.referrer = referrer;
        this.ip = ip;
    }

    public Long getId() { return id; }
    public Url getUrl() { return url; }
    public LocalDateTime getClickedAt() { return clickedAt; }
    public String getReferrer() { return referrer; }
    public String getIp() { return ip; }
}
