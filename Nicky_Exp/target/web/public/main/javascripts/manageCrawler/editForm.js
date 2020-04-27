Vue.component("edit-form", {
    props: ['id', 'source'],
    template:
    '   <div class="modal fade" tabindex="-1" role="dialog" :id="id">' +
    '       <div class="modal-dialog modal-lg" role="document">' +
    '           <div class="modal-content">' +
    '               <div class="modal-header">' +
    '                   <h5 class="modal-title">Edit: {{ source.sourceName }}</h5>' +
    '                   <button type="button" class="close" data-dismiss="modal" aria-label="Close">' +
    '                       <span aria-hidden="true">&times;</span>' +
    '                   </button>' +
    '               </div>' +
    '               <div class="modal-body">' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">sourceName</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input class="w-100" type="text" v-model="mSourceName">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">collectionName</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input class="w-100" type="text" v-model="mCollectionName">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">rawCollectionName</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input class="w-100" type="text" v-model="mRawCollectionName">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">enabled</label>' +
    '                       <div class="col-sm-8">' +
    '                           <input class="w-100" type="checkbox" id="checkbox" v-model="mEnabled">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">crawlingPeriod</label>' +
    '                       <div class="col-sm-8">' +
    '                           <input type="text" v-model="mCrawlingN">' +
    '                           <select v-model="mTimeUnit">' +
    '                               <option value="sec">sec</option>' +
    '                               <option value="min">min</option>' +
    '                               <option value="hour">hour</option>' +
    '                           </select>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">authType</label>' +
    '                       <div class="col-sm-8">' +
    '                           <select v-model="mAuthType">' +
    '                               <option value="oauth">oauth</option>' +
    '                               <option value="APIKey">APIKey</option>' +
    '                           </select>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row">' +
    '                       <label class="col-sm-4">authInfo</label><div class="col-sm-8"></div>' +
    '                   </div>' +
    '                   <div v-show="mAuthType === oauth">' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- consumerKey</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mConsumerKey"></div>' +
    '                       </div>' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- consumerSecret</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mConsumerSecret"></div>' +
    '                       </div>' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- accessToken</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mAccessToken"></div>' +
    '                       </div>' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- accessTokenSecret</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mAccessTokenSecret"></div>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div v-show="mAuthType === apiKey">' +
    '                       <div class="row attribute-row">' +
    '                           <label class="col-sm-4">- apiKey</label>' +
    '                           <div class="col-sm-8"><input class="w-100" type="text" v-model="mApiKey"></div>' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">apiFormat</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input type="text" class="w-100" v-model="mApiFormat">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">recordAccess</label>' +
    '                           <div class="col-sm-8">' +
    '                           <input type="text" class="w-100" v-model="mRecordAccess">' +
    '                       </div>' +
    '                   </div>' +
    '                   <div class="row attribute-row" >' +
    '                       <label class="col-sm-4">recordMapping</label>' +
    '                           <div class="col-sm-8">' +
    '                           <textarea type="text" class="w-100" v-model="mRecordMapping"></textarea>' +
    '                       </div>' +
    '                   </div>' +
    '               </div>' +
    '               <div class="modal-footer">' +
    '                   <button type="button" class="btn btn-primary" v-on:click="updateSource()" data-dismiss="modal">Save changes</button>' +
    '                   <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>' +
    '               </div>' +
    '           </div>' +
    '       </div>' +
    '   </div>',
    created: function() {
        // init default value
        this.mSourceName = this.source.sourceName;
        this.mCollectionName = this.source.collectionName;
        this.mRawCollectionName = this.source.rawCollectionName;
        this.mEnabled = this.source.enabled;
        this.mCrawlingN = this.source.crawlingPeriod[0];
        this.mTimeUnit = this.source.crawlingPeriod[1];
        this.mAuthType = this.source.authType;
        this.mAuthInfo = this.source.authInfo;
        this.mConsumerKey = this.mAuthInfo.consumerKey;
        this.mConsumerSecret = this.mAuthInfo.consumerSecret;
        this.mAccessToken = this.mAuthInfo.accessToken;
        this.mAccessTokenSecret = this.mAuthInfo.accessTokenSecret;
        this.mApiKey = this.mAuthInfo.apiKey;
        this.mApiFormat = this.source.apiFormat;
        this.mRecordAccess = this.source.recordAccess;
        this.mRecordMapping = JSON.stringify(this.source.recordMapping, null, 2)
    },
    data: function() {
        return {
            mSourceName: '',
            mCollectionName: '',
            mRawCollectionName: '',
            mEnabled: '',
            mCrawlingN: '',
            mTimeUnit: '',
            mAuthType: '',
            mAuthInfo: '',
            mConsumerKey: '',
            mConsumerSecret: '',
            mAccessToken: '',
            mAccessTokenSecret: '',
            mApiKey: '',
            mApiFormat: '',
            mRecordAccess: '',
            mRecordMapping: {},
            oauth: "oauth",
            apiKey: "APIKey"
        }
    },
    computed: {

    },
    methods: {
        updateSource: function() {
            // update authInfo depending on authType
            switch (this.mAuthType) {
                case "oauth":
                    this.mAuthInfo = {
                        consumerKey: this.mConsumerKey,
                        consumerSecret: this.mConsumerSecret,
                        accessToken: this.mAccessToken,
                        accessTokenSecret: this.mAccessTokenSecret
                    };
                    break;
                case "APIKey":
                    this.mAuthInfo = {
                        apiKey: this.mApiKey
                    };
                    break
            }
            // call the ajax js routing
            let xhr = new XMLHttpRequest();
            xhr.open('PUT', jsRoutes.controllers.ManageCrawlerController.updateSource().url);
            xhr.setRequestHeader("Content-Type", "text/json");
            let vm = this;
            xhr.onload = function() {
                if (xhr.status === 200) {
                    // show the updated toasted
                    Vue.toasted.show(vm.mSourceName + " is updated!", {
                        theme: "bubble",
                        position: "bottom-center",
                        duration : 2000
                    });
                    // refresh the crawlers
                    vm.$emit('update-crawlers')
                }
                else {
                    console.log('Request failed.  Returned status of ')
                    console.log(xhr)
                }
            };
            xhr.send(JSON.stringify({
                sourceName: this.mSourceName,
                collectionName: this.mCollectionName,
                rawCollectionName: this.mRawCollectionName,
                enabled: this.mEnabled,
                crawlingPeriod: [parseInt(this.mCrawlingN), this.mTimeUnit],
                authType: this.mAuthType,
                authInfo: this.mAuthInfo,
                apiFormat: this.mApiFormat,
                recordAccess: this.mRecordAccess,
                recordMapping: JSON.parse(this.mRecordMapping)
            }));
        }
    }
});