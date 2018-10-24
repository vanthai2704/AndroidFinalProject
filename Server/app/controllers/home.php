<?php

class home extends Controller
{

    public function index()
    {
        if (!empty($_SESSION['user'])) {
            // Trường hợp đã đăng nhập
            echo "Welcome webservice";
        } else {
            // Chưa đăng nhập
            echo "Not login! Please login to user API service";
        }
    }

    public function login()
    {
        if(empty($_POST['username'])){
            echo "Username not null";
            return;
        }
        if(empty($_POST['password'])){
            echo "Password not null";
            return;
        }
        $processLogin = $this->model('LoginModel');
        $processLogin->user_name = $_POST['username'];
        $processLogin->password = $_POST['password'];
        $resultLogin = $processLogin->login();
        if ($resultLogin['success']) {
            $resultSession = $processLogin->createSession();
            echo json_encode($resultSession);
        } else {
            echo $resultLogin['message'];
        }
        return;
    }

    public function logout()
    {
        $exit = $this->model('LoginModel');
        if ($exit->processLogout()) {
            // cách đầy data ra ngoài view hiển thị resultMessage bên ngoài view sẽ nhận dc là $data['resultMessage']
            echo "Logout success";
            return;
        }
    }
}