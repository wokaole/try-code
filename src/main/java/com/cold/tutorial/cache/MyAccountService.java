package com.cold.tutorial.cache;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by faker on 2015/10/19.
 */
public class MyAccountService {

    private MyCacheManager<Account> cacheManager;

    private MyAccountService() {
        this.cacheManager = new MyCacheManager<>();
    }

    /**
     * spring cache 实现, 当condition 满足时才进行缓存
     * @param name
     * @return
     */
    @Cacheable(value = "accountCache", condition = "#name.length() <= 4")
    private Account getAccountBySpring(String name) {
        Account account = getAccountFromDB(name);
        return account;
    }

    //组合 key
    @Cacheable(value = "accountCache", key = "#name.concat(#password)")
    public Account getAccountBySpring(String name, String password, boolean showLog) {
        return new Account();
    }

    //更新操作，会删除accountCache key的缓存
    @CacheEvict(value = "accountCache", key = "#account.getName")
    public void updateAccount(Account account) {
        update(account);
    }

    @CachePut(value = "accountCache", key = "#account.getName")
    public Account updateAccountAndPut(Account account) {
        return new Account();
    }

    public void update(Account account) {
        System.out.println("update account...");
    }

    //缓存更新
    @CacheEvict(value = "accountCache", allEntries = true)
    private void reloadAll() {

    }

    /**
     * 自定义的缓存实现
     * @param name
     * @return
     */
    private Account getAccount(String name) {
        Account account = cacheManager.getValue(name);
        if (account != null) {
            System.out.println("get account from cache");
            return account;
        }

        account = getAccountFromDB(name);
        if (account != null) {
            cacheManager.addOrUpdate(name, account);
        }
        return account;
    }

    private void reload() {
        cacheManager.removeAll();
    }

    private Account getAccountFromDB(String name) {
        System.out.println("get account from db");
        return new Account(name);
    }

    public static void main(String[] args) {
        MyAccountService accountService = new MyAccountService();
        accountService.getAccount("db");
        accountService.getAccount("db");

        System.out.println("remove all");
        accountService.reload();

        accountService.getAccount("db");
        accountService.getAccount("db");

    }
}
