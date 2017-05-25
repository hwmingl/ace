
copyright.factory("dataLoadService", ['$http','$cookies',
        function ($http,$cookies) {
            return {
                get: function (url) {
                    return $http({method: 'GET', url: url});
                },
                get: function (url, params) {
                    return $http({
                        method: 'GET',
                        url: url,
                        params: params
                    });
                },
                put: function (url, data) {
                    return $http({
                        method: 'PUT',
                        url: url,
                        data: data
                    });
                },
                post: function (url, data) {
                    return $http({
                        method: 'POST',
                        url: url,
                        data: data
                    });
                },
                delete: function (url, params) {
                    return $http.delete(url, {
                        params: params
                    });
                }
            }
        }]
)
;
