Vue.component("value-text", {
    props: ['value'],
    template: '<p class="value-text">{{ formattedValue }}</p>',
    computed: {
        formattedValue: function() {
            switch (typeof this.value){
                case 'string':
                    // remove quotes
                    return this.value.replace(/"/g,"");
                case 'object':
                    // format the object
                    return JSON.stringify(this.value, null, 2);
                default:
                    return this.value;
            }
        }
    }
});
