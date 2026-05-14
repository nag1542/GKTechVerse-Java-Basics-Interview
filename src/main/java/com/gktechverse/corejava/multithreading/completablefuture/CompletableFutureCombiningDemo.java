package com.gktechverse.corejava.multithreading.completablefuture;

import com.gktechverse.corejava.multithreading.DemoLogger;

import java.util.concurrent.CompletableFuture;

/**
 * 5) Combining multiple CompletableFutures: thenCombine, allOf, anyOf.
 */
public class CompletableFutureCombiningDemo {

    public static void main(String[] args) {
        run();
    }

    public static void run() {
        String id = "U-1004";

        CompletableFuture<CompletableFutureDemoSupport.User> userCF =
                CompletableFuture.supplyAsync(() -> CompletableFutureDemoSupport.fetchUser(id));
        CompletableFuture<CompletableFutureDemoSupport.Prefs> prefsCF =
                CompletableFuture.supplyAsync(() -> CompletableFutureDemoSupport.fetchPrefs(id));

        CompletableFuture<CompletableFutureDemoSupport.UserProfile> profile = userCF
                .thenCombine(prefsCF, CompletableFutureDemoSupport.UserProfile::new);

        CompletableFuture<CompletableFutureDemoSupport.User> u =
                CompletableFuture.supplyAsync(
                		() -> CompletableFutureDemoSupport.fetchUser(id));
        CompletableFuture<CompletableFutureDemoSupport.Orders> o =
                CompletableFuture.supplyAsync(
                		() -> CompletableFutureDemoSupport.fetchOrders(id));
        CompletableFuture<CompletableFutureDemoSupport.Metrics> m =
                CompletableFuture.supplyAsync(
                		() -> CompletableFutureDemoSupport.fetchMetrics(id));

        CompletableFuture<Void> allDone = CompletableFuture.allOf(u, o, m);
        
        CompletableFuture<CompletableFutureDemoSupport.Dashboard> dashboard 
        = allDone.thenApply(v ->
                new CompletableFutureDemoSupport.Dashboard(
                		u.join(), 
                		o.join(), 
                		m.join()
                		));
        
        
        

        CompletableFuture<Object> fastest = CompletableFuture.anyOf(
                CompletableFuture.supplyAsync(() -> {
                    CompletableFutureDemoSupport.sleep(250);
                    return "server-1";
                }),
                CompletableFuture.supplyAsync(() -> {
                    CompletableFutureDemoSupport.sleep(150);
                    return "server-2";
                }),
                CompletableFuture.supplyAsync(() -> {
                    CompletableFutureDemoSupport.sleep(300);
                    return "server-3";
                })
        );

        DemoLogger.info("thenCombine profile => " + profile.join());
        DemoLogger.info("allOf dashboard => " + dashboard.join());
        DemoLogger.info("anyOf fastest server => " + fastest.join());
        DemoLogger.info("Parallel total time trends to MAX(task time), not SUM(task times).");
        
        
        
        
    }
}
