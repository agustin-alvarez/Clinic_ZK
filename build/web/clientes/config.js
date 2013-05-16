

CKEDITOR.editorConfig = function(config) {
        config.language = 'es';
        config.extraPlugins = 'uicolor';
        config.resize_enabled = false;
        config.toolbar = 'MyToolbar';
        config.toolbar_MyToolbar = [
                      [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 
                        'Superscript', 'TextColor', 'BGColor', '-', 'Cut', 'Copy',
                        'Paste', 'Link', 'Unlink' ],
                      [ 'Undo', 'Redo', '-', 'JustifyLeft', 'JustifyCenter',
                        'JustifyRight', 'JustifyBlock' ],
                      [ 'Table', 'Smiley', 'SpecialChar', 'PageBreak',
                        'Styles', 'Format', 'Font', 'FontSize', 'Maximize',
                        'UIColor' ] ];
        config.uiColor = '#dbe5ee';
};