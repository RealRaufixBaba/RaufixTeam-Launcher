package git.raufixteam.launcher.authenticator;

import androidx.annotation.NonNull;

import git.raufixteam.launcher.authenticator.listener.LoginListener;
import git.raufixteam.launcher.authenticator.accounts.Account;

public interface BackgroundLogin {
    void createAccount(@NonNull LoginListener loginListener, String code);
    void refreshAccount(@NonNull LoginListener loginListener, Account account);
    interface Creator {
        BackgroundLogin create();
    }
}
