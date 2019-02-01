import fetch from 'utils/fetch';

export function loginByusername(username, password) {
  const data = {
    username,
    password
  };
  return fetch({
    url: '/login/signin',
    method: 'post',
    params: data
  });
}

export function logout() {
  return fetch({
    url: '/login/signout',
    method: 'post'
  });
}

export function getInfo() {
  return fetch({
    url: '/user/info',
    method: 'get',
  });
}

