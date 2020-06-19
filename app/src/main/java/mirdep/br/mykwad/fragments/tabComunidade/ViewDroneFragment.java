package mirdep.br.mykwad.fragments.tabComunidade;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import mirdep.br.mykwad.R;
import mirdep.br.mykwad.comum.Configs;
import mirdep.br.mykwad.objetos.Drone;
import mirdep.br.mykwad.repositorio.DroneRepositorio;
import mirdep.br.mykwad.repositorio.GlideApp;
import mirdep.br.mykwad.repositorio.PecaRepositorio;
import mirdep.br.mykwad.repositorio.UsuarioRepositorio;

public class ViewDroneFragment extends Fragment {
    private static final String NOME_LOG = "[ViewDrone]";

    private View root;
    private Drone drone;

    private TextView textView_viewdrone_nickname;
    private TextView textView_viewdrone_titulo;
    private ImageView imageView_viewdrone_foto;
    private View view_viewdrone_sair;
    private LinearLayout linearLayout_viewdrone_pecas;
    private TextView textView_viewdrone_descricao;

    public ViewDroneFragment(Drone drone){
        this.drone = drone;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_viewdrone, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inicializarInterface();
        atualizarUI();
    }

    private void inicializarInterface(){
        textView_viewdrone_nickname = root.findViewById(R.id.textView_viewdrone_nickname);
        textView_viewdrone_titulo = root.findViewById(R.id.textView_viewdrone_titulo);
        imageView_viewdrone_foto = root.findViewById(R.id.imageView_viewdrone_foto);
        view_viewdrone_sair = root.findViewById(R.id.view_viewdrone_sair);
        view_viewdrone_sair.setOnClickListener(v -> {
            fecharDialog();
        });
        linearLayout_viewdrone_pecas = root.findViewById(R.id.linearLayout_viewdrone_pecas);
        textView_viewdrone_descricao = root.findViewById(R.id.textView_viewdrone_descricao);
    }

    private void atualizarUI(){
        textView_viewdrone_titulo.setText(drone.getTitulo());
        UsuarioRepositorio.getInstance().getUsuario(drone.getUsuarioDonoId(), usuario -> textView_viewdrone_nickname.setText(usuario.getNickname()));
        textView_viewdrone_descricao.setText(drone.getDescricao());
        carregarFoto();
        carregarPecas();
    }

    private void carregarFoto() {
        GlideApp.with(getContext())
                .load(DroneRepositorio.getInstance().getStorageReference().child(drone.getId()).child(0+Configs.EXTENSAO_IMAGEM))
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .error(getResources().getDrawable(R.drawable.dronefoto))
                .into(imageView_viewdrone_foto);
    }

    private void carregarPecas(){
        if(drone.getAntenaId() != null && !drone.getAntenaId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Antena", drone.getAntenaId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("ANTENA: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }
        if(drone.getBateriaId() != null && !drone.getBateriaId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Bateria", drone.getBateriaId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("BATERIA: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }
        if(drone.getControladoraId() != null && !drone.getControladoraId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Controladora FC", drone.getControladoraId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("CONTROLADORA FC: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }
        if(drone.getCameraId() != null && !drone.getCameraId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Câmera", drone.getCameraId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("CÂMERA: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }if(drone.getEscId() != null && !drone.getEscId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Esc", drone.getEscId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("ESC: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }
        if(drone.getFrameId() != null && !drone.getFrameId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Frame", drone.getFrameId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("FRAME: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }if(drone.getHeliceId() != null && !drone.getHeliceId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Hélice", drone.getHeliceId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("HÉLICE: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }
        if(drone.getMotorId() != null && !drone.getMotorId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Motor", drone.getMotorId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("MOTOR: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }
        if(drone.getVideoTransmissorId() != null && !drone.getVideoTransmissorId().equals("0")){
            PecaRepositorio.getInstance().getPecaById("Vídeo Transmissor VTX", drone.getVideoTransmissorId(), peca -> {
                if(peca != null){
                    TextView tv = new TextView(getContext());
                    tv.setText("VTX: "+peca.toString());
                    linearLayout_viewdrone_pecas.addView(tv);
                }
            });
        }


    }



    public void fecharDialog(){
        getParentFragmentManager().popBackStackImmediate();
        Log.d(NOME_LOG,"MyDialog fechado!");
    }
}
