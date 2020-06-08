package mirdep.br.mykwad;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import mirdep.br.mykwad.main_tabs.tabComunidade.ComunidadeFragment;
import mirdep.br.mykwad.main_tabs.tabCriarDrone.CriarDroneFragment;
import mirdep.br.mykwad.main_tabs.tabMinhaConta.LoginFragment;
import mirdep.br.mykwad.main_tabs.tabMinhaConta.MinhaContaFragment;
import mirdep.br.mykwad.main_tabs.tabMinhaConta.RegistrarFragment;
import mirdep.br.mykwad.repositorio.PecaRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioAuthentication;

public class BaseApp extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private BottomNavigationView navView;

    private final String TAG_TAB = "[BASE_APP]";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_app);
        configurarNavView();
        selecionarTab(2);
        PecaRepositorio.getInstance().povoarBD();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configurarNavView(){
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
    }

    public void selecionarTab(int tabPos){
        switch(tabPos){
            case 1:
                navView.setSelectedItemId(R.id.navigation_criardrone);
                abrirTabCriarDrone();
                break;
            case 2:
                navView.setSelectedItemId(R.id.navigation_comunidade);
                abrirTabComunidade();
                break;
            case 3:
                navView.setSelectedItemId(R.id.navigation_minhaconta);
                abrirTabMinhaConta();
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

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //================= ABRIR FRAGMENT ===============
    public void abrirTabCriarDrone(){
        if(UsuarioAuthentication.getInstance().usuarioEstaLogado()){
            openFragment(new CriarDroneFragment());
        } else {
            selecionarTab(3);
        }
    }

    public void abrirTabComunidade(){
        openFragment(new ComunidadeFragment());
    }

    public void abrirTabMinhaConta(){
        if(UsuarioAuthentication.getInstance().usuarioEstaLogado()){
            openFragment(new MinhaContaFragment());
        } else {
            abrirTabLogin();
        }
    }

    public void abrirTabLogin(){
        openFragment(new LoginFragment());
    }

    public void abrirTabRegistrar(){
        openFragment(new RegistrarFragment());
    }
}
