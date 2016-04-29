package h4311.hexanome.insa.lyonrewards.view.qrcode;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.google.zxing.client.android.camera.open.ReflectionUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import h4311.hexanome.insa.lyonrewards.R;

/**
 * Created by Jonathan on 27/04/2016.
 */
public class QrReaderFragment extends Fragment implements QRCodeReaderView.OnQRCodeReadListener {

    private static final String QR_CODE_NOT_RECOGNIZED = "Ce QR code n'est pas reconnu par l'application";

    private OnQrCodeFoundListener mCallback;

    @BindView(R.id.qrdecoderview)
    protected QRCodeReaderView myDecoderView;

    private boolean processingQrCode;

    private String lastValue;

    private static Toast currentToast = null;

    public static QrReaderFragment newInstance(Bundle bundle) {
        QrReaderFragment fragment = new QrReaderFragment();
        fragment.setArguments(bundle);

        ReflectionUtils.performInjection();

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnQrCodeFoundListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnQrCodeFoundListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_qr_reader, container, false);
        ButterKnife.bind(this, view);

        processingQrCode = false;

        myDecoderView.setOnQRCodeReadListener(this);

        return view;
    }

    private void processQrCode(String data) {
        if(data.equals(lastValue)) {
            return;
        }

        Log.d("QREader", "Value : " + data);

        if (!processingQrCode && mCallback != null) {
            QrCodeContent qrCodeContent = getQrCodeContent(data);
            if (qrCodeContent != null) {
                processingQrCode = true;
                mCallback.onQrCodeFound(qrCodeContent);
            }
            else {
                if(currentToast != null) {
                    currentToast.cancel();
                }

                currentToast = Toast.makeText(getContext(), QR_CODE_NOT_RECOGNIZED, Toast.LENGTH_SHORT);
                currentToast.show();
            }
        }
        lastValue = data;
    }

    private QrCodeContent getQrCodeContent(String qrCodeValue) {
        Pattern qrCodePattern = Pattern.compile("(\\d+)&(\\d+)");
        Matcher m = qrCodePattern.matcher(qrCodeValue);
        if (m.matches()) {
            int actId = Integer.parseInt(m.group(1));
            int eventId = Integer.parseInt(m.group(2));

            Log.d("QR_CODE_PARSER", "ActId : " + actId);
            Log.d("QR_CODE_PARSER", "EventId : " + eventId);

            return new QrCodeContent(qrCodeValue, actId, eventId);
        }
        return null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        processQrCode(text);
    }

    @Override
    public void cameraNotFound() {
        // TODO
        return;
    }

    @Override
    public void QRCodeNotFoundOnCamImage() {
        return;
    }

    @Override
    public void onResume() {
        super.onResume();

        myDecoderView.getCameraManager().startPreview();
        lastValue = null;
        processingQrCode = false;
    }

    @Override
    public void onPause() {
        super.onPause();

        myDecoderView.getCameraManager().stopPreview();
        lastValue = null;
        processingQrCode = false;
    }
}
