package mirdep.br.mykwad;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mirdep.br.mykwad.ui.tabComunidade.ComunidadeFragment;
import mirdep.br.mykwad.ui.tabCriarDrone.CriarDroneFragment;
import mirdep.br.mykwad.ui.tabMinhaConta.MinhaContaFragment;
import mirdep.br.mykwad.usuario.UsuarioRepositorio;
import mirdep.br.mykwad.ui.tabMinhaConta.LoginFragment;
import mirdep.br.mykwad.ui.tabMinhaConta.RegistrarFragment;

public class BaseApp extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.base_app);
        configurarNavView();

    }

    private void configurarNavView(){
        navView = findViewById(R.id.nav_view);
        abrirTabComunidade();
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int tabId = item.getItemId();
        if(tabId != navView.getSelectedItemId()){
            switch (tabId) {
                case R.id.navigation_criar: {
                    abrirTabCriarDrone();
                    break;
                }
                case R.id.navigation_comunidade: {
                    abrirTabComunidade();
                    break;
                }
                case R.id.navigation_minhaconta: {
                    abrirTabMinhaConta();
                    break;
                }
            }
        }
        return true;
    }

    public void abrirTabMinhaConta(){
        Fragment fragment;
        if(UsuarioRepositorio.usuarioEstaLogado()){
            fragment = new MinhaContaFragment();
        } else {
            fragment = new LoginFragment();
        }
        openFragment(fragment);
    }

    public void abrirTabComunidade(){
        Fragment fragment = new ComunidadeFragment();
        openFragment(fragment);
    }

    public void abrirTabRegistrarConta(){
        openFragment(new RegistrarFragment());
    }

    public void abrirTabCriarDrone(){
        if(UsuarioRepositorio.usuarioEstaLogado()){
            Fragment fragment = new CriarDroneFragment();
            openFragment(fragment);
        } else {
            abrirTabMinhaConta();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
