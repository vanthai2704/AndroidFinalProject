<?php

class App
{
    protected $controller = 'home';
    protected $method = 'index';
    protected $params = [];
    
    public function __construct()
    {
        // print($_GET['url']);
        $url = $this->parseUrl();
        // var_dump($url);die();
        // get controller has in url
        // tồn tại url trong file control thì gán bằng url
        if (file_exists('../app/controllers/' . $url[0] . '.php')) {
            $this->controller = $url[0];
            unset($url[0]);
        }
        require_once '../app/controllers/' . $this->controller . '.php';
        // return Object
        $this->controller = new $this->controller;

        // get the action has in url
        if (isset($url[1])) {
            if (method_exists($this->controller, $url[1])) {
                $this->method = $url[1];
                unset($url[1]);
            }
        }

        $this->params = $url ? array_values($url) : [];
        call_user_func_array([$this->controller, $this->method], $this->params);
    }

    // this function parse url to get controller, action or params
    public function parseUrl()
    {
        // var_dump($_GET['url']);die();
        if (isset($_GET['url'])) {// lay dc url locfilter la loc?
            $url = explode('/', filter_var(rtrim($_GET['url'], '/'), FILTER_SANITIZE_URL));
            return $url;
        }

    }

}