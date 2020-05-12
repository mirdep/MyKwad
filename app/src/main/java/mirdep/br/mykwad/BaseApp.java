package mirdep.br.mykwad;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mirdep.br.mykwad.comum.MetodosGerais;
import mirdep.br.mykwad.pecas.Antena;
import mirdep.br.mykwad.pecasRepositorio.AntenaRepositorio;
import mirdep.br.mykwad.ui.tabComunidade.ComunidadeFragment;
import mirdep.br.mykwad.ui.tabCriarDrone.CriarDroneFragment;
import mirdep.br.mykwad.ui.tabMinhaConta.MinhaContaFragment;
import mirdep.br.mykwad.usuario.AutenticacaoRepositorio;
import mirdep.br.mykwad.ui.tabMinhaConta.LoginFragment;
import mirdep.br.mykwad.ui.tabMinhaConta.RegistrarFragment;

public class BaseApp extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    BottomNavigationView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_app);

        configurarNavView();

    }

    private void configurarNavView(){
        navView = findViewById(R.id.nav_view);
        abrirComunidade();
        navView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_criar: {
                abrirCriarDrone();
                break;
            }
            case R.id.navigation_comunidade: {
                abrirComunidade();
                break;
            }
            case R.id.navigation_minhaconta: {
                abrirMinhaConta();
                break;
            }
        }
        return true;
    }

    public void abrirMinhaConta(){
        Fragment fragment;
        if(AutenticacaoRepositorio.usuarioLogado()){
            getSupportActionBar().setTitle("Minha conta");
            fragment = new MinhaContaFragment();
        } else {
            getSupportActionBar().setTitle("Login");
            fragment = new LoginFragment();
        }
        openFragment(fragment);
    }

    public void abrirComunidade(){
        getSupportActionBar().setTitle("Comunidade");
        Fragment fragment = new ComunidadeFragment();
        openFragment(fragment);
    }

    public void abrirRegistrarConta(){
        getSupportActionBar().setTitle("Criar conta");
        openFragment(new RegistrarFragment());
    }

    public void abrirCriarDrone(){
        if(AutenticacaoRepositorio.usuarioLogado()){
            getSupportActionBar().setTitle("Criar drone");
            Fragment fragment = new CriarDroneFragment();
            openFragment(fragment);
        } else {
            abrirMinhaConta();
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void initialize() {
        Antena antena = new Antena("TBS", "ha", 35.25, "SMA");
        antena.setId(MetodosGerais.encodeId("abcde"));
        new AntenaRepositorio().salvarAntena(antena, false);
    }


}
