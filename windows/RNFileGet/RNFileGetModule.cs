using ReactNative.Bridge;
using System;
using System.Collections.Generic;
using Windows.ApplicationModel.Core;
using Windows.UI.Core;

namespace Com.Reactlibrary.RNFileGet
{
    /// <summary>
    /// A module that allows JS to share data.
    /// </summary>
    class RNFileGetModule : NativeModuleBase
    {
        /// <summary>
        /// Instantiates the <see cref="RNFileGetModule"/>.
        /// </summary>
        internal RNFileGetModule()
        {

        }

        /// <summary>
        /// The name of the native module.
        /// </summary>
        public override string Name
        {
            get
            {
                return "RNFileGet";
            }
        }
    }
}
