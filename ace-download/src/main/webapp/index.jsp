<html>
<head>
    <link type="text/css" rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/smoothness/jquery-ui.min.css" media="screen" />
    <link type="text/css" rel="stylesheet" href="https://www.plupload.com/plupload/js/jquery.ui.plupload/css/jquery.ui.plupload.css" media="screen" />
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.js" charset="UTF-8"></script>
    <script type="text/javascript">
        // Initialize the widget when the DOM is ready
        $(function() {
            $("#uploader").plupload({
                // General settings
                runtimes : 'html5,flash,silverlight,html4',
                url : "/examples/upload",

                // Maximum file size
                max_file_size : '2mb',

                chunk_size: '1mb',

                // Resize images on clientside if we can
                resize : {
                    width : 200,
                    height : 200,
                    quality : 90,
                    crop: true // crop to exact dimensions
                },

                // Specify what files to browse for
                filters : [
                    {title : "Image files", extensions : "jpg,gif,png"},
                    {title : "Zip files", extensions : "zip,avi"}
                ],

                // Rename files by clicking on their titles
                rename: true,

                // Sort files
                sortable: true,

                // Enable ability to drag'n'drop files onto the widget (currently only HTML5 supports that)
                dragdrop: true,

                // Views to activate
                views: {
                    list: true,
                    thumbs: true, // Show thumbs
                    active: 'thumbs'
                },

                // Flash settings
                flash_swf_url : '/plupload/js/Moxie.swf',

                // Silverlight settings
                silverlight_xap_url : '/plupload/js/Moxie.xap'
            });
        });
    </script>
</head>
<body>
<h2>Hello World!</h2>
<div id="themeswitcher" class="pull-right"> </div>
<script>
    $(function() {
        $.fn.themeswitcher && $('#themeswitcher').themeswitcher({cookieName:''});
    });
</script>
<div id="uploader">
    <p>Your browser doesn't have Flash, Silverlight or HTML5 support.</p>
</div>


<script type="text/javascript" src="https://www.plupload.com/js/bootstrap.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://www.plupload.com/js/shCore.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://www.plupload.com/js/shBrushPhp.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://www.plupload.com/js/shBrushjScript.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://www.plupload.com/plupload/js/plupload.full.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://www.plupload.com/plupload/js/jquery.ui.plupload/jquery.ui.plupload.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="https://www.plupload.com/js/themeswitcher.js" charset="UTF-8"></script>

</body>
</html>