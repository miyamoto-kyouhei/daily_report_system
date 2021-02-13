package models;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "sales_calls")
@NamedQueries({
        @NamedQuery(name = "getAllSalesCalls", query = "SELECT s FROM SalesCalls AS s ORDER BY s.id ASC"),
})
@Entity
public class SalesCalls {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 営業訪問ID
     */
    private Integer id;

    @Column(name = "visit_time", nullable = false)
    /**
     * 訪問時間
     */
    private LocalTime visit_time;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    /**
     * 取引先
     */
    private Client client;

    @Lob
    @Column(name = "sales_report", nullable = false)
    /**
     * 営業訪問内容
     */
    private String sales_report;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getVisit_time() {
        return visit_time;
    }

    public void setVisit_time(LocalTime visit_time) {
        this.visit_time = visit_time;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getSales_report() {
        return sales_report;
    }

    public void setSales_report(String sales_report) {
        this.sales_report = sales_report;
    }

}
