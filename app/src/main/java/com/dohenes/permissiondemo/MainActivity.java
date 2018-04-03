package com.dohenes.permissiondemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dohenes.permissiondemo.util.JumpToPermissionManagementUtils;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

// 1.Register an Activity or Fragment(we support both) to handle permissions.
//   注册一个Activity或Fragment(我们支持两者)来处理权限。[必须的注解]
@RuntimePermissions
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.main_btn_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NOTE: delegate the permission handling to generated method.
                // 注意：将权限处理委托给生成的方法。
//                MainActivityPermissionsDispatcher.showCameraWithPermissionCheck(MainActivity.this);
                JumpToPermissionManagementUtils.goToPermissionSettingPage(MainActivity.this);
            }
        });
    }

    // 2.Annotate a method which performs the action that requires one or more permissions.
    //   注解一个执行需要获取一个或多个权限的操作的方法。[必须的注解]
    @NeedsPermission(Manifest.permission.CAMERA)
    void showCamera() {
        Toast.makeText(this, R.string.permission_camera_get, Toast.LENGTH_SHORT).show();
    }

    // 3.Annotate a method which explains why the permission/s is/are needed. It passes in a
    // PermissionRequest object which can be used to continue or abort the current
    // permission request upon user input.
    // 注解一种解释为什么需要权限的方法(注解用户首次拒绝后，再次获取权限时给出提示的方法)。它通过PermissionRequest对象，
    // 该对象可用于在用户输入时继续或中止当前权限请求。[非必须的注释]
    @OnShowRationale(Manifest.permission.CAMERA)
    void showRationaleForCamera(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_camera_rationale)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                        Log.e(TAG, "proceed---");
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                        Log.e(TAG, "cancel...");
                    }
                })
                .show();
    }

    // 4.Annotate a method which is invoked if the user doesn't grant the permissions
    //  注解一个用户拒绝授权时回调的方法。[非必须的注释]
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void showDeniedForCamera() {
        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();
    }

    // 5.Annotate a method which is invoked if the user chose to have the device "never ask again"
    // about a permission
    // 注解一个用户选择"不再询问"时进行提示的回调方法。[非必须的注释]
    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void showNeverAskForCamera() {
        Log.e(TAG, "show Never...");
        Toast.makeText(this, R.string.permission_camera_never_ask_again, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        // 注意：将权限处理委托给生成的方法。
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
