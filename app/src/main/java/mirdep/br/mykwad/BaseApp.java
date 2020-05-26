package mirdep.br.mykwad;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mirdep.br.mykwad.drones.PecaRepositorio;
import mirdep.br.mykwad.tabs.tabComunidade.ComunidadeFragment;
import mirdep.br.mykwad.tabs.tabCriarDrone.CriarDroneFragment;
import mirdep.br.mykwad.tabs.tabMinhaConta.MinhaContaFragment;
import mirdep.br.mykwad.usuario.UsuarioRepositorio;
import mirdep.br.mykwad.tabs.tabMinhaConta.LoginFragment;
import mirdep.br.mykwad.tabs.tabMinhaConta.RegistrarFragment;

public class BaseApp extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navView;

    private final String TAG_TAB = "Mudança de tab: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.base_app);
        configurarNavView();
        abrirTab(2);
        PecaRepositorio.povoarBD();
    }

    private void configurarNavView(){
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
    }

    public void abrirTab(int tabPos){
        switch(tabPos){
            case 1:
                navView.setSelectedItemId(R.id.navigation_criardrone);
                break;
            case 2:
                navView.setSelectedItemId(R.id.navigation_comunidade);
                break;
            case 3:
                navView.setSelectedItemId(R.id.navigation_minhaconta);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int tabId = item.getItemId();
        if(tabId != navView.getSelectedItemId()){
            switch (tabId) {
                case R.id.navigation_criardrone: {
                    Log.d(TAG_TAB, "Mudou para CriarDrone");
                    abrirTabCriarDrone();
                    break;
                }
                case R.id.navigation_comunidade: {
                    Log.d(TAG_TAB, "Mudou para Comunidade");
                    abrirTabComunidade();
                    break;
                }
                case R.id.navigation_minhaconta: {
                    Log.d(TAG_TAB, "Mudou para MinhaConta");
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

    public void abrirTabRegistrarConta(){
        openFragment(new RegistrarFragment());
    }

    public void abrirTabComunidade(){
        Fragment fragment = new ComunidadeFragment();
        openFragment(fragment);
    }

    public void abrirTabCriarDrone(){
        if(UsuarioRepositorio.usuarioEstaLogado()){
            Fragment fragment = new CriarDroneFragment();
            openFragment(fragment);
        } else {
            abrirTab(3);
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
