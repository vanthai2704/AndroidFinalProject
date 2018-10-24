<?php

//
class LoginModel extends Controller
{
    public $id;
    public $user_name;
    public $password;
    public $fullname;
    public $result;

    public function login()
    {
        // chuyển tên đăng nhập thành chữ thường
        $this->user_name = strtolower($this->user_name);
        if ($this->user_name == '') {
            return $this->result = "chua nhap tai khoan";
        }
        if ($this->password == '') {
            return $this->result = "chua nhap mat khau";
        }
//        $this->password = md5($this->password);
        $sql = "SELECT * FROM `user` WHERE `username`='{$this->user_name}' and `password`='{$this->password}'";
        // cách gọi vào model connect từ model
        $conModel = $this->model('Connect');
        // thực hiện câu lệnh
        $result = $conModel->getConnect($sql);

        if (!$result) {
            $message = $this->result = "Tài khoản hoặc mật khẩu bị sai";
            return ['success' => false, 'message' => $message];
        }
        if ($result->num_rows > 0) {
//            while ($row = $result->fetch_assoc()) {
//
//            }
            $message = $this->result = "Đăng nhập thành công";
            return ['success' => true, 'message' => $message];
        } else {
            $message = $this->result = "Tài khoản hoặc mật khẩu bị sai";
            return ['success' => false, 'message' => $message];
        }
    }

    public function processLogout()
    {
        session_start();
        unset($_SESSION['user']);
        session_destroy();
        $this->result = "Đăng xuất thành công ";
        return true;
    }

    public function createSession()
    {
        session_start();
        $user_session = $this->rand_char(10);
        $expired_at = time() + 15 * 60;
        $_SESSION[$this->user_name . $user_session]['expired_at'] = $expired_at;
        return ['session' => $user_session, 'expired_at' => $expired_at];
    }

    public function rand_char($length)
    {
        $random = '';
        for ($i = 0; $i < $length; $i++) {
            $random .= chr(mt_rand(33, 126));
        }
        return $random;
    }
}