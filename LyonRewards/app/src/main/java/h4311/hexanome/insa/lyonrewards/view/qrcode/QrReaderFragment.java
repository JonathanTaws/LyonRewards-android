package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Jonathan on 27/04/2016.
 */
public class QrReaderFragment extends Fragment {

    @BindView(R.id.camera_view)
    protected SurfaceView mSurfaceView;

    public static QrReaderFragment newInstance(Bundle bundle) {
        QrReaderFragment fragment = new QrReaderFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_reader, container, false);
        ButterKnife.bind(this, view);

        QREader.getInstance().setUpConfig();

        QREader.getInstance().start(getActivity().getApplicationContext(), mSurfaceView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                Log.d("QREader", "Value : " + data);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
