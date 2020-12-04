package choco;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface ChocolateService {

    @SqlQuery("select * from chocolate")
    @RegisterBeanMapper(ChocolateBar.class)
    List<ChocolateBar> getBars();

    @SqlUpdate("insert into chocolate (name, qty) values (?, ?)")
    void createBar(String name, int qty);

    @SqlUpdate("update chocolate set name=:name, qty=:qty where id=:id")
    void updateBar(@Bind("id") int id, @Bind("name") String name, @Bind("qty") int qty);

    @SqlQuery("select * from chocolate where name = ?")
    @RegisterBeanMapper(ChocolateBar.class)
    ChocolateBar getBarByName(String name);

    @SqlQuery("select * from chocolate where name = ?")
    @RegisterBeanMapper(ChocolateBar.class)
    ChocolateBar getBarById(int id);

    @SqlUpdate("update chocolate set qty = qty - 1 where id = ?")
    void eatOneMoreBar(int id);

    @SqlUpdate("delete from chocolate where id = ?")
    void deleteBar(int id);



}
