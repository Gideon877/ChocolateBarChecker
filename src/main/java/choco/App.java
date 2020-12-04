package choco;

import com.google.gson.Gson;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import static spark.Spark.*;

import java.util.List;

public class App {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:postgresql://localhost:5432/choco");
        jdbi.installPlugin(new SqlObjectPlugin());

        staticFiles.location("/public"); // Static files

        Gson gson = new Gson();

        post("/api/chocolates/eat", (req, res) -> {
            System.out.println(req.body());
            String json = req.body();
            ChocolateBar chocolateBar = gson.fromJson(json, ChocolateBar.class);

            ChocolateBar bar = jdbi.withHandle(h -> {
                ChocolateService chocolateService = h.attach(ChocolateService.class);
                return chocolateService.getBarByName(chocolateBar.getName());
            });


            jdbi.useHandle(h -> {
                ChocolateService service = h.attach(ChocolateService.class);
                if(bar.getQty() > 1) {
                    service.eatOneMoreBar(bar.getId());
                } else {
                    service.deleteBar(bar.getId());
                }
            });

            ChocolateBar bars = jdbi.withHandle(h -> {
                ChocolateService chocolateService = h.attach(ChocolateService.class);
                return chocolateService.getBarByName(bar.getName());
            });

            return bars;
        }, gson::toJson);


        post("/api/chocolates/remove", (req, res) -> {
            System.out.println(req.body());
            String json = req.body();
            ChocolateBar chocolateBar = gson.fromJson(json, ChocolateBar.class);

            ChocolateBar bar = jdbi.withHandle(h -> {
                ChocolateService chocolateService = h.attach(ChocolateService.class);
                return chocolateService.getBarByName(chocolateBar.getName());
            });

            jdbi.useHandle(h -> {
                ChocolateService service = h.attach(ChocolateService.class);
                service.deleteBar(bar.getId());
            });

            ChocolateBar bars = jdbi.withHandle(h -> {
                ChocolateService chocolateService = h.attach(ChocolateService.class);
                return chocolateService.getBarByName(chocolateBar.getName());
            });

            return bars;
        }, gson::toJson);

        post("/api/chocolates", (req, res) -> {
            System.out.println(req.body());
            String json = req.body();
            ChocolateBar chocolateBar = gson.fromJson(json, ChocolateBar.class);

            jdbi.useHandle(h -> {
                ChocolateService service = h.attach(ChocolateService.class);
                ChocolateBar chocolate = service.getBarByName(chocolateBar.getName());
                if(chocolate == null) {
                    service.createBar(chocolateBar.getName(), chocolateBar.getQty());
                } else {
                    service.updateBar(chocolate.getId(), chocolate.getName(), chocolate.getQty() + chocolateBar.getQty());
                }
            });

            ChocolateBar bars = jdbi.withHandle(h -> {
                ChocolateService chocolateService = h.attach(ChocolateService.class);
                return chocolateService.getBarByName(chocolateBar.getName());
            });

            return bars;
        }, gson::toJson);

        get("/api/chocolates", (req, res) -> {

            List<ChocolateBar> bars = jdbi.withHandle(h -> {
                ChocolateService chocolateService = h.attach(ChocolateService.class);
                return chocolateService.getBars();
            });

            return bars;

        }, gson::toJson);

    }

}
