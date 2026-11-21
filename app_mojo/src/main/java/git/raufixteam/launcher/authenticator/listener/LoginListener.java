package git.raufixteam.launcher.authenticator.listener;

import git.raufixteam.launcher.authenticator.accounts.Account;

public interface LoginListener{
    void onLoginDone(Account account);
    void onLoginError(Throwable errorMessage);
    void onLoginProgress(int step);
    void setMaxLoginProgress(int max);
}
