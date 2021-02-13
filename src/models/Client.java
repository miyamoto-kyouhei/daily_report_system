package models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "clients")
@NamedQueries({
        @NamedQuery(name = Client.All_CLIENTS, query = "SELECT c FROM Client AS c ORDER BY c.id ASC"),
        @NamedQuery(name = Client.NOT_DELETED_ALL_CLIENTS, query = "SELECT c FROM Client AS c WHERE c.delete_flag = 0 ORDER BY c.id ASC"),
        @NamedQuery(name = Client.CLIENTS_COUNT, query = "SELECT COUNT(c) FROM Client AS c")
})
@Entity
public class Client {

    public static final String All_CLIENTS = "getAllClients";
    public static final String NOT_DELETED_ALL_CLIENTS = "getNotDeletedAllClients";
    public static final String CLIENTS_COUNT = "getClientsCount";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * 取引先ID
     */
    private Integer id;

    @Column(name = "name", nullable = false)
    /**
     * 取引先名
     */
    private String name;

    @Column(name = "created_at", nullable = false)
    /**
     * 登録日時
     */
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    /**
     * 更新日時
     */
    private Timestamp updated_at;

    @Column(name = "delete_flag", nullable = false)
    /**
     * 削除フラグ
     */
    private Integer delete_flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(Integer delete_flag) {
        this.delete_flag = delete_flag;
    }
}
